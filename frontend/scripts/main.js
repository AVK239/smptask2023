// Login and logout

document.querySelector(".login__main_btn").addEventListener("click", function() {
  var name = document.querySelector(".login__main_login").value;
  var password = document.querySelector(".login__main_password").value;

  if ((name == "1111")&&(password == "1111")) {
    document.querySelector(".login__img").style.display = "none";
    document.querySelector(".login__text").style.display = "none";
    document.querySelector(".login__main").style.display = "none";

    document.querySelector(".header").style.display = "unset";
    document.querySelector(".main").style.display = "unset";

    document.querySelector(".login__main_login").value = "";
    document.querySelector(".login__main_password").value = "";
  }
  else {
    alert("Login error!\n\nWrong login or password.\nTry again!")
  }
});

document.querySelector(".header__exit_btn").addEventListener("click", function() {
  document.querySelector(".login__img").style.display = "unset";
  document.querySelector(".login__text").style.display = "flex";
  document.querySelector(".login__main").style.display = "flex";

  document.querySelector(".header").style.display = "none";
  document.querySelector(".main").style.display = "none";
});


// Сворачивание меню

document.querySelector(".header__title_btn").addEventListener("click", function() {
  var header = document.querySelector(".header");

  if (header.offsetWidth == 250) {
    header.style.transition = "width 0.3s";
    header.style.width = "80px";

    document.querySelector(".header__title_1").style.display = "none";
    document.querySelector(".header__title_2").style.display = "none";
    document.querySelector(".header__title_img").src = "images/header/menu_closed.png";

    document.querySelector(".header__nav").style.transition = "margin-left 0.3s";
    document.querySelector(".header__nav").style.marginLeft = "17px";
    document.querySelectorAll(".header__nav_text").forEach(elem => {
      elem.style.display = "none";
    });
    
    document.querySelector(".header__exit_btn").style.transition = "margin-left 0.3s";
    document.querySelector(".header__exit_btn").style.marginLeft = "17px";
    document.querySelector(".header__exit_text").style.display = "none";
  }
  else {
    header.style.transition = "width 0.3s";
    header.style.width = "250px";

    document.querySelector(".header__title_1").style.display = "unset";
    document.querySelector(".header__title_2").style.display = "unset";
    document.querySelector(".header__title_img").src = "images/header/menu_opened.png";

    document.querySelector(".header__nav").style.transition = "margin-left 0.3s";
    document.querySelector(".header__nav").style.marginLeft = "30px";
    document.querySelectorAll(".header__nav_text").forEach(elem => {
      setTimeout(() => {
        elem.style.display = "unset";
      }, 150);
    });
    
    document.querySelector(".header__exit_btn").style.transition = "margin-left 0.3s";
    document.querySelector(".header__exit_btn").style.marginLeft = "30px";
    setTimeout(() => {
      document.querySelector(".header__exit_text").style.display = "unset";
    }, 150);
  }
});


// Выбор пункта меню

document.querySelector(".header__nav_btn").style.backgroundImage = "linear-gradient(to right, var(--dark-blue), var(--super-light-blue), var(--main-blue))";
document.querySelector(".header__nav_img-container").style.border = "1px solid white";
document.querySelector(".header__nav_text").style.color = "white";
document.querySelector(".header__nav_img").src = document.querySelector(".header__nav_img").src.slice(0, -4) + "_white.png";

document.querySelectorAll(".header__nav_btn").forEach(elem => {
  elem.addEventListener("click", function() {
    document.querySelectorAll(".header__nav_btn").forEach(cur_elem => {
      cur_elem.style.backgroundImage = "unset";
      cur_elem.children[0].style.border = "1px solid var(--main-blue)";
      cur_elem.children[1].style.color = "var(--light-blue)";
      if (cur_elem.children[0].children[0].src.lastIndexOf("_white.png") !== -1) {
        cur_elem.children[0].children[0].src = cur_elem.children[0].children[0].src.slice(0, -10) + ".png";
      };
    });

    elem.style.backgroundImage = "linear-gradient(to right, var(--dark-blue), var(--super-light-blue), var(--main-blue))";
    elem.children[0].style.border = "1px solid white";
    elem.children[1].style.color = "white";
    if (elem.children[0].children[0].src.lastIndexOf("_white.png") == -1) {
      elem.children[0].children[0].src = elem.children[0].children[0].src.slice(0, -4) + "_white.png";
    };
  });
});


// Отображение пунктов меню

document.querySelector(".section__1").style.display = "unset";

document.querySelectorAll(".header__nav_btn").forEach(elem => {
  elem.addEventListener("click", function() {
    document.querySelectorAll(".section").forEach(cur_elem => {
      cur_elem.style.display = "none";
    });

    document.querySelector(".section__" + elem.classList[1].slice(-1)).style.display = "unset";
  });
});


// Секция 1

document.querySelector(".section__1_search_input").addEventListener("focus", function() {
  document.querySelector(".section__1_search").style.border = "1px solid black";
});

document.querySelector(".section__1_search_input").addEventListener("focusout", function() {
  document.querySelector(".section__1_search").style.border = "1px solid var(--light-gray)";
});

document.querySelectorAll(".section__1_main_elem").forEach(elem => {
  elem.addEventListener("mouseover", function() {
    elem.style.fontWeight = "700";
  });
});

document.querySelectorAll(".section__1_main_elem").forEach(elem => {
  elem.addEventListener("mouseout", function() {
    elem.style.fontWeight = "400";
  });
});

document.querySelector(".section__1_search_input").addEventListener("input", function() {
  var text = document.querySelector(".section__1_search_input").value;

  document.querySelectorAll(".section__1_main_elem").forEach(elem => {
    if (elem.children[0].children[1].textContent.indexOf(text) == -1) {
      elem.style.display = "none";
    }
    else {
      elem.style.display = "flex";
    }
  });
});


// Поиск корзины

document.querySelector(".section__4_search_input").addEventListener("input", function() {
  var text = document.querySelector(".section__4_search_input").value;

  document.querySelectorAll(".section__4_main_elem").forEach(elem => {
    if (elem.children[0].textContent.indexOf(text) == -1) {
      elem.style.display = "none";
    }
    else {
      elem.style.display = "flex";
    }
  });
});

// Удаление корзины

document.querySelector(".section__4_delete").style.display = "none";

document.querySelector(".section__4_menu").addEventListener("click", function() {
  if (document.querySelector(".section__4_delete").style.display == "none") {
    document.querySelector(".section__4_delete").style.display = "unset";

    document.querySelectorAll(".section__4_main_elem_name_input").forEach(elem => {
      elem.style.display = "unset";
    });
  }
  else {
    document.querySelector(".section__4_delete").style.display = "none";

    document.querySelectorAll(".section__4_main_elem_name_input").forEach(elem => {
      elem.style.display = "none";
      elem.checked = false;
    });
  };
});

document.querySelector(".section__4_delete").addEventListener("click", function() {
  document.querySelectorAll(".section__4_main_elem_name_input").forEach(elem => {
    if (elem.checked == true) {
      elem.parentElement.parentElement.remove();
    };
  });
});


// Создание корзины

document.querySelector(".section__4_create").addEventListener("click", function() {
  document.querySelector(".section__4").style.display = "none";
  document.querySelector(".section__create_basket").style.display = "unset";
});

document.querySelector(".section__create_basket_title_btn").addEventListener("click", function() {
  document.querySelector(".section__4").style.display = "unset";
  document.querySelector(".section__create_basket").style.display = "none";
});

document.querySelector(".section__create_basket_main_left_buttons_clear").addEventListener("click", function() {
  document.querySelector(".section__create_basket_main_left_name_input").value = "";
  document.querySelector(".section__create_basket_main_left_features_right_capacity").value = "1";
  document.querySelector(".section__create_basket_main_left_features_right_validity").value = "180";
  document.querySelector(".section__create_basket_main_left_features_right_versioning_input input").checked = false;
  document.querySelector(".section__create_basket_main_left_features_right_object-locking_input input").checked = false;
  document.querySelector(".section__create_basket_main_left_features_right_quota_input input").checked = false;
  document.querySelector(".section__create_basket_main_left_features_right_retention_input input").checked = false;
  document.querySelector(".section__create_basket_main_left_features_right_mode_input-1").checked = true;
});

document.querySelector(".section__create_basket_main_left_buttons_create").addEventListener("click", function() {
  var name = document.querySelector(".section__create_basket_main_left_name_input").value;
  var check = new RegExp("^[a-z0-9]{3,64}$");
  
  if (check.exec(name)) {
    document.querySelector(".section__create_basket").style.display = "none";
    document.querySelector(".section__4").style.display = "unset";

    document.querySelector(".section__create_basket_main_left_name_input").value = "";
    document.querySelector(".section__create_basket_main_left_features_right_capacity").value = "1";
    document.querySelector(".section__create_basket_main_left_features_right_validity").value = "180";
    document.querySelector(".section__create_basket_main_left_features_right_versioning_input input").checked = false;
    document.querySelector(".section__create_basket_main_left_features_right_object-locking_input input").checked = false;
    document.querySelector(".section__create_basket_main_left_features_right_quota_input input").checked = false;
    document.querySelector(".section__create_basket_main_left_features_right_retention_input input").checked = false;
    document.querySelector(".section__create_basket_main_left_features_right_mode_input-1").checked = true;

    document.querySelector('.section__4_main_list').innerHTML +=
    `
      <li class="section__4_main_elem flex">
        <p class="section__4_main_elem_name">
          <input type="checkbox" class="section__4_main_elem_name_input">

          ` + name + `
        </p>

        <div class="section__4_main_elem_info flex">
          <p class="section__4_main_elem_created">
            Created: ` + new Date() + `
          </p>

          <p class="section__4_main_elem_access">
            Access: R/W
          </p>
        </div>

        <div class="section__4_main_elem_line"></div>

        <div class="section__4_main_elem_data flex">
          <img src="images/section_4/basket-blue.png" class="section__4_main_elem_main-img">

          <div class="section__4_main_elem_usage flex">
            <div class="section__4_main_elem_usage_container flex">
              <img src="images/section_4/usage.png" class="section__4_main_elem_usage_img">

              <p class="section__4_main_elem_usage_text">
                Usage
              </p>
            </div>

            <p class="section__4_main_elem_usage_data">
              0MiB
            </p>
          </div>

          <div class="section__4_main_elem_objects flex">
            <div class="section__4_main_elem_objects_container flex">
              <img src="images/section_4/objects.png" class="section__4_main_elem_objects_img">

              <p class="section__4_main_elem_objects_text">
                Objects
              </p>
            </div>

            <p class="section__4_main_elem_objects_data">
              0
            </p>
          </div>
        </div>
      </li>
    `
  }
  else {
    alert("Invalid bucket name!\n\nBucket names must be between 3 (min) and 64 (max) characters long.\nBucket names can consist only of lowercase letters and numbers.")
  }
});


// Просмотр корзины

document.querySelectorAll(".section__1_main_elem").forEach(elem => {
  elem.addEventListener("click", function() {
    document.querySelector(".section__1").style.display = "none";
    document.querySelector(".section__bucket").style.display = "unset";
  });
});

document.querySelectorAll(".section__bucket_main_down_main_elem").forEach(elem => {
  elem.addEventListener("click", function() {
    if (elem.children[0].checked == false) {
      elem.children[0].checked = true;
    }
    else {
      elem.children[0].checked = false;
    };
  });
});

document.querySelector(".section__bucket_main_down_main_info_input").addEventListener("change", function() {
  if (document.querySelector(".section__bucket_main_down_main_info_input").checked == false) {
    document.querySelectorAll(".section__bucket_main_down_main_elem").forEach(elem => {
      elem.children[0].checked = false;
    });
  }
  else {
    document.querySelectorAll(".section__bucket_main_down_main_elem").forEach(elem => {
      elem.children[0].checked = true;
    });
  }
});

document.querySelector(".section__bucket_main_down_delete").addEventListener("click", function() {
  document.querySelectorAll(".section__bucket_main_down_main_elem").forEach(elem => {
    if (elem.children[0].checked == true) {
      elem.remove();
    };
  });
});

document.querySelector(".section__bucket_search_input").addEventListener("input", function() {
  var text = document.querySelector(".section__bucket_search_input").value;

  document.querySelectorAll(".section__bucket_main_down_main_elem").forEach(elem => {
    if (elem.children[1].children[1].textContent.indexOf(text) == -1) {
      elem.style.display = "none";
    }
    else {
      elem.style.display = "flex";
    }
  });
});

document.querySelector(".section__bucket_title_btn").addEventListener("click", function() {
  document.querySelector(".section__bucket").style.display = "none";
  document.querySelector(".section__1").style.display = "unset";
});


// Информация о корзине

document.querySelectorAll(".section__4_main_elem").forEach(elem => {
  elem.addEventListener("click", function() {
    document.querySelector(".section__about").style.display = "unset";
    document.querySelector(".section__4").style.display = "none";
  });
});

document.querySelector(".section__about_title_btn").addEventListener("click", function() {
  document.querySelector(".section__about").style.display = "none";
  document.querySelector(".section__4").style.display = "unset";
});