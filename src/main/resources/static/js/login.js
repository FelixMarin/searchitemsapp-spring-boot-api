jQuery(document).ready(function ($) {
 let tab = $(".tabs h3 a");
  
 tab.on("click", function (event) {
    event.preventDefault();
    tab.removeClass("active");
    $(this).addClass("active");

    let tab_content = $(this).attr("href");
    $('div[id$="tab-content"]').removeClass("active");
    $(tab_content).addClass("active");
  });
});

$('#user_pass_login').on('keypress',function(e) {
  if(e.which == 13) {
    $( "#signup-id").click();
  }
});

$( "#signup-id").on("click",function(event) {
  
  event.preventDefault();

  let user = {
    username: $('#user_login').val(),
    password: $('#user_pass_login').val()
  };

  $.ajax({
    url : "/jwst/token",
    type: "POST",
    data : user,
    async: false,
    beforeSend: function (xhr) {
      xhr.setRequestHeader ("Authorization", "Basic " + btoa(user.username + ":" + user.password));
    },
    success: function(data, textStatus, xhr) {
      window.localStorage.setItem('sia_token', data);
      window.localStorage.setItem('access_basic', btoa(user.username + ":" + user.password));
			window.location.href="/main";
    },
    error: function(xhr) {      
      alert('El usuario no existe');
    }
});
  
});

$( "#login-id").on("click", function(event) {
  event.preventDefault();

  if(mailExists()) {
    alert('El usuario ya existe');
    return;
  }

  let user = {
    "username": $('#user_name').val(),
    "password": $('#user_pass_signup').val(),
    "email": $('#user_email').val()
  };

  $.ajax({
		contentType: 'application/json',
		data: JSON.stringify(user),
		async: false,
    cache: false,
    timeout: 30000,
		success: function(data){
      console.log(data);
      alert('New user added');
		},
		error: function(xhr, textStatus, error){
            console.log(xhr.responseText);
            console.log(xhr.statusText);
            console.log(textStatus);
            console.log(error);
    },
		processData: false,
		url: '/user/save',
		type: 'POST'
  });
});

function mailExists() {

  let isEmail = false;

  $.ajax({
    url : "/user/mailExists?mail=" + $('#user_email').val(),
    type: "GET",    
    async: false,
    success: function(data, textStatus, xhr) {
      isEmail = (data == true);
    },
    error: function(xhr) {      
      alert('Error al consultar el email');
    }
});  

return isEmail;
}

