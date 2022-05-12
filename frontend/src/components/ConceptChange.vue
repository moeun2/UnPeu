<template>
    <div class="concept-room">
        <div class="concept-div">
            <h2>컨셉 변경</h2>
            <a href="#setDate">
                <button>나만의 날짜 설정</button>
            </a>

            <div>
                <h2>배경화면 변경</h2>
                <button @click="changeBackground">
                    back
                </button>
            </div>

            <div>
                <h2>이벤트룸 변경</h2>
                <button @click="changeEvent">
                    event
                </button>
            </div>
        </div>
        




        <!-- modal 창 -->
        <div id="setDate" class="modal-window">
            <div class="modal-message">
                <a href="#">
                <button class="btn_red_cancel">
                    <span>X</span>
                </button>
                </a>            
                <!-- 날짜 입력 받기 -->
                <div class="concept-content">
                    <p class="setDate-p">본 날짜는 매년 정기적으로 실행됩니다</p>
                    <input type="number" name="month" class="input-date" id="month" v-model="month" placeholder="0"> 월
                    <input type="number" name="date" class="input-date" id="date" v-model="date" placeholder="0"> 일
                    <!-- alert창 띄우기-->
                    <button @click="setDate" type="submit" class="setDate-btn"> 설정 </button>
                </div>
            </div>
        </div>

    </div>
</template>

<script>
import * as Alert from "@/api/alert";

export default {
    name: "ConceptChange",
    data() {
        return {
            month: '',
            date: ''
        }
    },
    methods: {
        setDate() {
            if(this.month == null) {
                this.$swal.fire("Oops...!", "Month를 적어주세요!", "error");
            }
            else if(this.date == null) {
                this.$swal.fire("Oops...!", "date을 적어주세요!", "error");
            }
            else if(this.month < 1 || this.month > 12) {
                this.$swal.fire("Oops...!", "Month는 1 ~ 12월까지 있어요!", "error");
            }
            else if(this.month == 2 && (this.date < 1 || this.date > 28)) {
                this.$swal.fire("Oops...!", "2월의 일 범위를 벗어났어요!", "error");
            }
            else if((this.month == 1 ||
                    this.month == 3 ||
                    this.month == 5 ||
                    this.month == 7 ||
                    this.month == 8 ||
                    this.month == 10 ||
                    this.month == 12) &&
                    (this.date < 1 || this.date > 31)) {
                this.$swal.fire("Oops...!", "일 범위를 벗어났어요!", "error");
            }
            else if((this.month == 4 ||
                    this.month == 6 ||
                    this.month == 9 ||
                    this.month == 11) &&
                    (this.date < 1 || this.date > 30)) {
                this.$swal.fire("Oops...!", "일 범위를 벗어났어요!", "error");
            }
            else {
                this.$store.commit("eventStore/SET_DATE", this.date);
                this.$store.commit("eventStore/SET_MONTH", this.month);
                Alert.setMonthAndDate(this);
                //modal 닫게 처리
            }
            
        },
        changeBackground() {
            //document.body.style.backgroundImage = "url(https://i.imgur.com/yBs2YNe.jpg)";
            document.getElementsByClassName("view").backgroundImage = 'url(https://i.imgur.com/yBs2YNe.jpg)';
        },
        changeEvent() {
            document.getElementById("main-room").style.backgroundImage = 'url(https://i.imgur.com/JzNuJr5.png)';
        }
    }
}
</script>

<style lang="scss">
.concept-room {
    background-color: #ddefa5;
    height: 435px;
    padding: 23px;
}
.concept-div {
    background-color: #fff;
    height: 390px;
    border-radius: 5px;
}
.concept-content {
    padding: 10px;
}
.setDate-p {
    margin-bottom: 5px;
}
.input-date {
    height: 30px;
    width: 50px;
    border-bottom: solid rgb(37, 37, 37) 1px;
}
.setDate-btn {
    float: right;
    width: 60px;
    height: 30px;
    color: #fff;
    border-radius: 15px;
    background-color: #4caf50;
}
</style>