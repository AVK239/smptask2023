import FrontendController from "../../src/test/java/com/minio/tpu/FrontendController.java";


document.getElementById("getBuckets").addEventListener("click", function() {
  fetch("FrontendController/buckets") // Путь к вашему эндпоинту для получения списка корзин
    .then(response => response.json())
    .then(data => {
      console.log("Список корзин:", data);
      // Дополнительный код для обработки данных
    })
    .catch(error => {
      console.error("Ошибка:", error);
    });
});

document.getElementById("createBucket").addEventListener("click", function() {
  fetch("FrontendController/buckets", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ bucketName: "Корзина" }) // Замените на нужное название
  })
    .then(response => response.json())
    .then(data => {
      console.log("Ответ после создания корзины:", data);
      // Дополнительный код для обработки данных
    })
    .catch(error => {
      console.error("Ошибка:", error);
    });
});
