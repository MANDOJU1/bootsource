// 제목 클릭 시 a 태그 기능 중지
// data-id 에 있는 값 가져오기
document.querySelector("tbody").addEventListener("click", (e) => {
  e.preventDefault();
  const target = e.target;

  console.log(target.dataset.id);

  // id 를 이용해 한 개 데이터 가져오기
  // 변수 사용 ` `
  fetch(`http://localhost:8080/read/${target.dataset.id}`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      // 디자인영역 가져오기
      document.querySelector("#category").value = data.categoryName;
      document.querySelector("#title").value = data.title;
      document.querySelector("#publisher").value = data.publisherName;
      document.querySelector("#writer").value = data.writer;
      document.querySelector("#price").value = data.price;
      document.querySelector("#salePrice").value = data.salePrice;
      document.querySelector("#book_id").value = data.id;
    });
});

// 삭제 클릭 시 id 가져오기
document.querySelector(".btn-primary").addEventListener("click", (e) => {
  // a 태그 - 움직이기 때문에 일단 기능 중지
  e.preventDefault();
  const id = document.querySelector("#book_id ").value;
  console.log(id);

  // /delete/{id} + Delete
  // fetch(`주소`, {상세 - get방식이 아니라면 method 는 무조건 사용})
  // then 꼭 안 받아도 됨 / 받아서 무언가 하고자 할 때 받음
  // response 로 controller에서 리턴한 success 받아 다음 then 에서 실행할 내용 작성
  fetch(`/delete/${id}`, {
    method: "delete",
  })
    .then((response) => response.text())
    .then((data) => {
      if (data == "success") {
        alert("삭제 성공");
        location.herf = "/book/list?page=1&type=&keyword=";
      }
    });
});

// 수정 클릭 시
document.querySelector(".btn-secondary").addEventListener("click", (e) => {
  // a 태그가 가진 기능 중지
  e.preventDefault();

  // form 내용 가져오기 → javascript 객체 생성
  // form 이 여러 개일 때 - 특정 폼 안에 있는 요소 찾기
  // const myForm = document.querySelector("#myForm");
  // myForm 안에 들어있는 요소 찾기
  // myForm.querySelector("#book_id")
  const book_id = document.querySelector("#book_id").value;
  const data = {
    id: book_id,
    price: document.querySelector("#price").value,
    salePrice: document.querySelector("#salePrice").value,
  };

  console.log(data);

  // method 지정 안하면 get 으로 전송됨
  fetch(`/modify/${book_id}`, {
    method: "put",
    headers: {
      "content-type": "application/json",
    },
    // JSON.stringify() : javascript 객체 → json 타입으로 변환
    body: JSON.stringify(data),
  })
    .then((response) => response.text())
    .then((data) => {
      if (data == "success") {
        alert("수정성공");
        location.href = "/book/list?page=1&type=&keyword=";
      }
    });
});
