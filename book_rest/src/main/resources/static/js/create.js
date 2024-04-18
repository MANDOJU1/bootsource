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

  // method 지정 안하면 get 으로 전송됨
  //// fetch 는 method를 지정하지 않으면 get(기본)으로 전송됨
  fetch(`http://localhost:8080/book/new`, {
    method: "post",
    headers: {
      "content-type": "application/json",
    },
    // JSON.stringify() : javascript 객체 → json 타입으로 변환
    body: JSON.stringify(data),
  })
    .then((response) => response.text())
    .then((data) => {
      if (data == "success") alert("입력성공");
      location.href = "/book/list?page=1&type=&keyword=";
    });
});
