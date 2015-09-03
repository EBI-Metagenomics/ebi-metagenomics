#!/usr/bin/env perl

use strict;
use warnings;

#Get the input file from the command line
my $file = shift;
unless(defined($file) and -s $file){
  die "No file specified or the file does not exist!\n";
}


#Read in this input file, simply as a 2D array.
my $raw = readFile($file);

#Now build the tree from the 2D array
my $tree = buildTree($raw);


#Here comes the fun bit, setting the left/rights as we walk over the tree
# As we go down, increment the lft 
$tree->{lft} = 1;
my ($l, $r) = walkTree($tree, 1, 1);

#As we come back up, increment the rgt
$r++;
$tree->{rgt} = $r;

#Print out the tree in a tabular format. 
printTree($tree);

#Print out the tree as SQL insert statements
printSQLInsertStatements($tree);


#Read input tsv file and produce a two dimensional array.
sub readFile {
  my ($file) = @_;
  my @raw;
  open(F, '<', $file) or die "Failed to open $file:[$!]\n";
  while ( my $line = <F>){
    chomp($line);   
    my @line = split(/\t/, $line);
    my @lineCheck;
    foreach(@line){
      next if(/Unclassified/);
      push(@lineCheck,$_); 
    }
    
    push(@raw, \@lineCheck);
  }
  close(F);
  return (\@raw);
}


#Build the tree which will be a set of nested hashes. This calls a private method
#(_buildBranch) which is the recursion method.
sub buildTree {
  my ($raw) = @_;
  my $depth = 1;
  my $lineage = 'root';
  my $uid = 0; 
  my $tree = { name => 'root', lft => 0, rgt => 0, children => [], uid => $uid, lineage => $lineage, depth => $depth }; 
  foreach my $nodes (@$raw){
    $uid = _buildBranch($nodes, $tree, $depth, $lineage, $uid); 
  } 
  return($tree);
}

sub _buildBranch {
  my ($nodes, $tree, $depth, $lineage, $uid) = @_;
  my $nodeName = shift (@$nodes);
  $depth++;
  return ($uid) if($nodeName eq 'Unclassified');
  
  $lineage .= ":$nodeName";
  my $node;
  foreach my $c (@{$tree->{children}}){
    if ($c->{name} eq $nodeName){
      #Already seen it
      $node = $c;
    }
  }
  if(!defined($node)){
    $uid++;
    $node = { name => $nodeName, lft => 0, rgt => 0, children => [], uid => $uid, lineage => $lineage, depth => $depth};
    push(@{$tree->{children}}, $node);
  }

  if(scalar(@$nodes)){
    $uid = _buildBranch($nodes, $node, $depth, $lineage, $uid);  
  }
  return($uid);
}


sub walkTree {
  my ($tree, $lft, $rgt) = @_;
  
  foreach my $c (@{$tree->{children}}){
    $lft++;
    $c->{lft} = $lft;
    if(scalar($c->{children})){
      ($lft, $rgt) = walkTree($c, $lft, $rgt);
    }
    $rgt = $lft + 1 if($lft >= $rgt);
    $c->{rgt} = $rgt;
    $lft = $rgt;
  }
  return($lft, $rgt);
}


sub printTree {
  my ($tree) = @_;

  print $tree->{uid}."\t".$tree->{lft}."\t".$tree->{rgt}."\t".$tree->{depth}."\t".$tree->{name}."\t".$tree->{lineage}."\n";
  foreach my $c (sort {$a->{lft} <=> $b->{rgt}} @{$tree->{children}}){
    printTree($c);
  }
}

sub printSQLInsertStatements {
  my ($tree) = @_;

  print "INSERT INTO biome_hierarchy_tree (biome_id,lft,rgt,depth,biome_name,lineage) values (".$tree->{uid}.",".$tree->{lft}.",".$tree->{rgt}.",".$tree->{depth}.",'".$tree->{name}."','".$tree->{lineage}."');\n";
  foreach my $c (sort {$a->{lft} <=> $b->{rgt}} @{$tree->{children}}){
    printSQLInsertStatements($c);
  }
}
