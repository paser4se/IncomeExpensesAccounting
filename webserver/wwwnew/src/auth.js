let authenticated = false;
export default {
  authenticated() {
    return authenticated;
  },
  setAuthenticated(value) {
    authenticated = value;
  },
  authenticate() {
    return fetch('http://localhost:8085/iea/api/auth/authenticate', {
      method: 'GET',
      credentials: 'include',
    })
      .then(res => {
        if (res.status == 200 && localStorage.getItem("isLoggedIn") != "true") {
          localStorage.setItem("isLoggedIn", "true");
        }
        else if (res.status == 403) {
          localStorage.setItem("isLoggedIn", "false");
        }
        return Promise.resolve(res);
      });
  }
};
