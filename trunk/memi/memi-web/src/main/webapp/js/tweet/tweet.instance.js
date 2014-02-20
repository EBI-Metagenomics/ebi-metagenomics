//Fetch 20 latest tweets, but filter out @replies, and display only 3:
jQuery(function ($) {
    $(".tweet").tweet({
//      Twitter queries do not work, because older tweets are not indexed
//        query:"#GSC",
//      How many tweets to display?
        count:3,
        fetch:20,
//      Filter which removes Twitter replies
        filter:function (t) {
            return !/^@\w+/.test(t.tweet_raw_text);
        },
//      Twitter screen names
        username:"EBImetagenomics",
//      The following template attribute eliminate the date stamps and avatars
        template:"{text}",
        loading_text:"loading tweets..."
    });
});

//Fetch 20 latest tweets filtered by hash tag
//jQuery(function ($) {
//    $(".tweet").tweet({
////      How many tweets to display?
//        count:3,
//        fetch:20,
////      Filter which shows hash tags with #GSC and #Guysbeard
//        filter:function (t) {
//            return /#(GSC|Guysbeard)+/.test(t.tweet_raw_text);
//        },
////      Twitter screen names
//        username:"EBImetagenomics",
////      The following template attribute eliminate the date stamps and avatars
//        template:"{text}",
//        loading_text:"loading tweets..."
//    });
//});