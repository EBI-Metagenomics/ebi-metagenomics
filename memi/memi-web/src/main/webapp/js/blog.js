(function(document, getJSON) {
  var dummyElement = document.createElement('p');
  var blogURL = 'https://ebi-metagenomics.github.io/blog/';

  var processPost = function(data, source) {
    var dest = source.cloneNode(true);
    // process title
    dest.querySelector('h2').innerText = data.title;
    // process content
    dummyElement.innerHTML = data.excerpt;
    dest.querySelector('.home_box > div').innerHTML = dummyElement.textContent;
    // process links
    var links = dest.querySelectorAll('.home_box > a');
    // Read more
    links[0].href = data.url;
    if (data.emg) {
      links[1].href = data.emg.url;
      links[1].innerText = links[1].title = data.emg.text;
    } else {
      links[1].parentElement.removeChild(links[1]);
    }
    source.parentElement.replaceChild(dest, source);
  };

  // You didn't see anything...
  var hideContainers = function() {
    var blogSection = document.getElementById('blog');
    blogSection.parentElement.removeChild(blogSection);
  };

  var handleData = function(data) {
    try {
      processPost(data.spotlight, document.getElementById('blog-spotlight'));
      processPost(data.tools, document.getElementById('blog-tools'));
    } catch (err) {
      handleError({statusText: err});
    }
  };

  var handleError = function(jqXHR) {
    console.error(jqXHR.statusText);
    hideContainers();
  };

  var main = function() {
    getJSON(blogURL + 'feed-first-of-each.json')
      .done(handleData)
      .fail(handleError);
  };

  if (document.readyState !== 'loading') {
    main();
    return;
  }

  document.addEventListener('DOMContentLoaded', main, {once: true});
})(document, jQuery.getJSON);
