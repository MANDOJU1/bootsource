// 체크박스 클릭시 id 가져오기

// 화면의 중복 요소에 이벤트 작성 (방법 1)
// document.querySelectorAll('[name="completed"]').addEventListener("click", (e) => {});

// 이벤트 전파 => 부모요소가 감지 (방법 2)
document.querySelector(".list-group").addEventListener("click", (e) => {
  console.log("이벤트가 발생한 대상 " + e.target);
  // 체크박스 클릭시 id 가져오기
  console.log("이벤트가 발생한 대상 value " + e.target.value);
  console.log("이벤트를 감지한 대상 " + e.currentTarget);

  // get방식일 때
  // location.href = "/todo/update?id="+e.target.value;

  const form = document.querySelector("#completedForm");
  form.querySelector('[name="id"]').value = e.target.value;
  form.submit();
});
