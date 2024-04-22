// 페이지 로드 시 무조건 실행
// 즉시 실행 함수
// fetch() - 함수 작성 후 호출

// 함수 작성
// 1. function 함수명(){}
// 2. const(or let) 이름 = () => {}

// 함수 실행 →
// method1();

// 호이스팅 : 선언 안 하고 먼저 호출 후 선언
// 1번은 호이스팅 되고, 2번은 안됨

// var 로 선언된 변수는 호이스팅 됨
// const, let 은 안 됨

// 날짜 포맷 변경 함수
const formatDate = (data) => {
  const date = new Date(data);

  return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
};

// 댓글목록 보여줄 영역 가져오기
const replyList = document.querySelector(".replyList");
// 댓글 목록 가져오기
const replyLoaded = () => {
  fetch(`/replies/board/${bno}`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      // var length = data.length;

      // data 개수 가져오기 (댓글 총 수 확인)
      document.querySelector(".d-inline-block").innerHTML = data.length;

      let result = "";
      data.forEach((reply) => {
        result += `<div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno="${reply.rno}">`;
        result += `<div class="p-3"><img src="/img/default.png" alt="" class="rounded-circle mx-auto d-block" style="width: 60px; height: 60px" /></div>`;
        result += `<div class="flex-grow-1 align-self-center"><div>${reply.replyer}</div>`;
        result += `<div><span class="fs-5">${reply.text}</span></div>`;
        result += `<div class="text-muted"><span class="small">${formatDate(reply.createdDate)}</span></div></div>`;
        result += ` <div class="d-flex flex-column align-self-center">`;
        result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
        result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div></div></div>`;
      });
      // 영역에 result 보여주기
      replyList.innerHTML = result;
    });
};

replyLoaded();

// 새 댓글 등록
// 새 댓글 등록 폼 submit 시
// submit 기능 중지 / 작성자 / 댓글 가져오기 → 스크립트 객체로 변경
const replyForm = document.querySelector("#replyForm");
replyForm.addEventListener("submit", (e) => {
  e.preventDefault();
  const replyer = document.querySelector("#replyer");
  const text = document.querySelector("#text");
  // 새 글인지 수정인지 어떻게 구분 ? rno가 있는지 없는지 확인
  // 수정하는 경우에 값이 들어옴
  const rno = document.querySelector("#rno");

  const reply = {
    replyer: replyer.value,
    text: text.value,
    bno: bno,
    rno: rno.value,
  };

  if (!rno.value) {
    // 새 댓글 등록
    fetch(`/replies/new`, {
      method: "post",
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify(reply),
    })
      .then((response) => response.text())
      .then((data) => {
        if (data) {
          alert(data + " 번 댓글 등록");

          // replyForm 내용 제거
          replyer.value = "";
          text.value = "";

          replyLoaded();
        }
      });
  } else {
    // 댓글 수정
    fetch(`/replies/${rno.value}`, {
      method: "put",
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify(reply),
    })
      .then((response) => response.text())
      .then((data) => {
        if (data) {
          alert(data + " 번 댓글 수정");

          // replyForm 내용 제거
          replyer.value = "";
          text.value = "";
          rno.value = "";

          replyLoaded();
        }
      });
  }
});

// 테스트는 언제나 부분부분 차근히 가장 처음에는 이벤트가 잘 발생하는지

// 이벤트 전파 : 자식 요소에 일어난 이벤트는 상위 요소로 전달 됨
// 댓글 삭제/수정 버튼 클릭 시 이벤트 전파로 찾아오기
// rno 가져오기
replyList.addEventListener("click", (e) => {
  // 실제 이벤트가 일어난 대상은 누구인가? e.target
  const btn = e.target;

  // closest("요소") : 제일 가까운 상위요소 찾기
  // rno 클래스명 - data- 로 들어있는 건 dataset 으로 가져옴
  const rno = btn.closest(".reply-row").dataset.rno;
  console.log("rno", rno);

  // 삭제 or 수정 버튼이 눌러졌을 때만 동작
  // 삭제 or 수정 버튼이 클릭이 되었는지 구분하기
  if (btn.classList.contains("btn-outline-danger")) {
    fetch(`/replies/${rno}`, {
      method: "delete",
    })
      .then((response) => response.text())
      .then((data) => {
        if (data == "success") {
          alert("댓글 삭제 성공");
          // 새로고침
          replyLoaded();
        }
      });
  } else if (btn.classList.contains("btn-outline-success")) {
    // rno에 해당하는 댓글을 가져온 후 댓글 등록 폼에 가져온 내용 보여주기
    fetch(`/replies/${rno}`, {
      method: "get",
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("데이터 가져오기");
        console.log(data);

        replyForm.querySelector("#rno").value = data.rno;
        replyForm.querySelector("#replyer").value = data.replyer;
        replyForm.querySelector("#text").value = data.text;
      });
  }
});
