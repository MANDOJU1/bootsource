// 삭제 버튼 클릭 시 actionForm 전송
document.querySelector(".btn-danger").addEventListener("click", () => {
  const form = document.querySelector("#actionForm");
  if (!confirm("삭제하시겠습니까?")) return;

  // form.action = "/guestbook/delete";을 여기서 쓰지않으면 modify.html에서
  // <form th:action="@{delete}" method="post" id="actionForm"> 사용하면 됨
  form.action = "/guestbook/delete";
  form.submit();
});
