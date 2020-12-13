// get values of names
function getParameter(name) {
    var exp = "(^|&)" + name + "=([^&]*)(&|$)";
    var reg = new RegExp(exp, "i");
    var queryStr = location.search.substr(1).trim();
    var r = queryStr.match(reg);
    if (r != null) return (r[2]);   // the second captured group ([^&]*)
    return null;
}