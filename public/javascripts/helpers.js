(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_GB/sdk.js#xfbml=1&version=v2.8";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

$(function(){
  $("ul#navi_containTab > li").click(function(event){
    var menuIndex=$(this).index();
    $("ul#detail_containTab > li:visible").hide();
    $("ul#detail_containTab > li").eq(menuIndex).show();
  });
});
