import store from '../store'

export function hasPermission(permission) {
  const myPermissions = store.getters.permissions
  return myPermissions.indexOf(permission) > -1
}
