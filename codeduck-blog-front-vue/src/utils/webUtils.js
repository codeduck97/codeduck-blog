
export function getUrlVars () {
  var vars = {}
  window.location.href.replace(
    /[?&]+([^=&]+)=([^&#]*)/gi,
    function (m, key, value) {
      vars[key] = value
    }
  )
  return vars
}
