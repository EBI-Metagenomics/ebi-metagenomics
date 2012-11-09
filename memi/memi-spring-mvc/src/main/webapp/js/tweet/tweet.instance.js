//Fetch 20 tweets, but filter out @replies, and display only 3:
jQuery(function ($) {
    $(".tweet").tweet({
//      How many tweets to display?
        count:3,
//      Filter which removes Twitter replies
        fetch:20,
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