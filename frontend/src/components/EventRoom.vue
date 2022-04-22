<template>
    <v-app>
        <div class="main-room">
            <div class="event-tab">
                <ul class="myMenu">
                    <li class="menu2">
                        MENU
                        <ul class="menu2_s submenu">
                            <li>컨셉 변경</li>
                            <li>초기화 설정</li>
                            <li>메세지 저장</li>
                        </ul>   
                    </li>
                </ul>
            </div>
            <div class="gift-box">
            <button class="reg-gift">
                <img src="https://i.imgur.com/vaBFer6.png" class="gift-img" alt="">
                <p>어른이날을 축하해주세요
                    <br/>
                    콩주머니 메세지 &amp; 선물 등록하러 가기!
                </p>
            </button>
            </div>
            <div class="pocket-area">
                <div class="item" v-for="message in messages" :key="message">
                    <a href="#pocket">
                        <button>
                            <img src="@/assets/img/pocket.png" class="pocket" alt="">
                        </button>
                    </a>
                </div>
            </div>

            <!-- modal 창 -->
                <div id="pocket" class="modal-window">
                    <div class="modal-message">
                        <a href="#">
                        <button class="btn_red_cancel">
                            <span>X</span>
                        </button>
                        </a>
                        <div class="modal-content">
                            <img src="https://i.imgur.com/vaBFer6.png" alt="" class="modal-img" > 
                            <p class="message-user">영등포 마취총</p>
                            <div class="message-box">나는 최고의 친구^-^!</div>
                        </div>
                    </div>
                </div>
        </div>
    </v-app>
</template>

<script>
export default {
    name: "MainPage",
    data() {
        return {
            messages: [
                {
                    content: 'hi',
                },
                {
                    content: 'hello'
                },
                {
                    content: 'bye'
                },
            ]
        }
    },
    created() {

    },
    methods: {
        eventBtn() {
            let tab = document.getElementById("menubars");
            if(tab.style.display === "none") {
                tab.style.display = "block";
            }else {
                tab.style.display = "none";
            }
        }
    }
}
</script>

<style lang="scss">
.pocket-area {
    margin-top: 40%;
    margin-left: 20px;
    padding-right: 20px;
    display: grid;
    grid-template-rows: repeat(auto-fill, minmax(90px, 1fr));
    grid-template-columns: repeat(auto-fill, minmax(90px, 1fr));
    width: 550px;
    height: 230px;
}
.item {
    display: flex;

}
.pocket {
    width: 75px;
    height: 70px;
}
.gift-img {
    width: 35px;
    height: 35px;
    float: left;
}
.reg-gift {
    background-color: #fff;
    border-radius: 15px;
    padding: 10px;
    width: 400px;
    text-align: center;
    box-shadow: 3px 3px 3px #abababd8;
    
}
.gift-box {
    text-align: center;
}
.event-tab {
    display: block;
    text-align: right;
}
.event-btn {
    width: 30px;
    height: 30px;
}

/* 드롭다운 메뉴 css */
ul.myMenu { 
    list-style: none; 
    margin: 5px;
}
ul.myMenu > li {
    display:inline-block; 
    width:80px; 
    padding:5px 10px; 
    text-align:center; 
    position:relative; 
}
ul.myMenu > li:hover { 
    background:rgba(255, 255, 255, 0.627); 
    border-radius: 30px;
}
ul.myMenu > li ul.submenu { 
    display:none; 
    position:absolute; 
    top:30px; 
    left:0; 
}
ul.myMenu > li:hover ul.submenu { 
    display:block; 
}
ul.myMenu > li ul.submenu > li { 
    display:inline-block; 
    width:100px; 
    padding:5px 10px; 
    background:#eee; 
    border:1px solid #eee; 
    text-align:center; 
}
ul.myMenu > li ul.submenu > li:hover { 
    background:#fff; 
}

/* modal창 css */

.modal-window {
  position: fixed;
  background-color: rgba(15, 15, 15, 0.25);
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  z-index: 999;
  visibility: hidden;
  opacity: 0;
  pointer-events: none;
  transition: all 0.3s;
  &:target {
    visibility: visible;
    opacity: 1;
    pointer-events: auto;
  }
  & > div {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    background-size: cover;
    padding: 7px;
  }
  header {
    font-weight: bold;
  }
  h1 {
    font-size: 150%;
    margin: 0 0 15px;
  }
}

.modal-close {
  color: black;
  line-height: 50px;
  font-size: 100%;
  position: absolute;
  right: 10%;
  text-align: center;
  top: 10%;
  width: 70px;
  text-decoration: none;
  &:hover {
    color: black;
  }
}

.modal-message {
    background-color: #fff;
    border-radius: 15px;
    width: 300px;
}

.modal-content {
    text-align: center;
}

.modal-img {
    width: 200px;
    height: 200px;
}

.message-user {
    font-size: 20px;
    font-weight: bold;
    padding-bottom: 5px;
}

.message-box {
    border: 1px solid rgb(221, 239, 165);
    border-radius: 15px;
}
</style>