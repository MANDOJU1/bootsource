// form submit 시 submit 기능 중지
document.querySelector("#createForm").addEventListener("submit", (e) => {
  // 태그가 가진 기능 중지
  e.preventDefault();

  // form 내용 가져오기 → javascript 객체 생성
  const data = {
    categoryName: document.querySelector("select[name='categoryName']").value,
    title: document.querySelector("#title").value,
    publisherName: document.querySelector("select[name='publisherName']").value,
    writer: document.querySelector("#writer").value,
    price: document.querySelector("#price").value,
    salePrice: document.querySelector("#salePrice").value,
  };

  console.log(data);

  // fetch 는 method를 지정하지 않으면 get(기본)으로 전송됨
  // 비동기 방식 : 서버가 응답을 언제할지 모름 서버의 응답을 기다리지 않음, 응답이 오면 .then()이 실행됨
  // fetch(`http://localhost:8080/book/new` : action 역할
  fetch(`http://localhost:8080/book/new`, {
    method: "post",
    // 이 부분은 필수 요소는 아니지만 json으로 가야하면 알려주어야 함
    headers: {
      "content-type": "application/json",
    },
    // JSON.stringify() : javascript 객체 → json 타입으로 변환
    // body: JSON.stringify(data) : input 역할
    body: JSON.stringify(data),
  })
    .then((response) => response.text())
    .then((data) => {
      if (data == "success") {
        alert("입력성공");
        location.href = "/book/list?page=1&type=&keyword=";
      }
    });
});
