//jQuery(function ($) {
//    $(".tweet").tweet({
////        username:"EBImetagenomics",
//        query:"from:EBImetagenomics ENA's",
//        join_text:"auto",
////        avatar_size:32,
//        count:3,
//        auto_join_text_default:"",
//        auto_join_text_ed:"",
//        auto_join_text_ing:"",
//        auto_join_text_reply:"",
//        auto_join_text_url:"",
//        loading_text:"loading tweets..."
//    });
//});
//Fetch 20 tweets, but filter out @replies, and display only 3:
jQuery(function ($) {
    $(".tweet").tweet({
        count:3,
        fetch:20,
        filter:function (t) {
            return !/^@\w+/.test(t.tweet_raw_text);
        },
        username:"EBImetagenomics",
//        The following template attribute eliminate the date stamps and avatars
        template:"{text}",
        loading_text:"loading tweets..."
    });
});