<script setup>
  import axios from 'axios';

  import { ref } from 'vue';
  import { useRouter } from 'vue-router'

  const router = useRouter();

  const API_URL = "http://localhost:8080/api/v1/pizzas";
  
  const newPizza = ref({
    name: "",
    description: "",
    url: "",
    price: 0
  })

  function savePizza(pizza) {
    axios
    .post(`${API_URL}`, pizza)
    .then(res => {
      if (res.status === 200) {
        router.push("/");
      }
      else {
        resetForm();
      }
    })
    .catch(error => {
      console.error(error);
      resetForm();
    })
  }

  function resetForm() {
    newPizza.value = {
      name: "",
      description: "",
      url: "",
      price: 0
    }
  }
</script>

<template>
  <main class="container py-5">
    <h1 class="text-center">NEW PIZZA</h1>

    <form method="post" @submit.prevent="savePizza(newPizza)">
      
      <div class="my-4">
        <div class="d-flex align-items-center gap-3">
          <label for="name" class="w-25">Name: </label>
          <input type="text" name="name" id="name" minlength="3" maxlength="100" placeholder="Type the name..." v-model="newPizza.name" required>
        </div>
      </div>
      
      <div class="my-4">
        <div class="d-flex align-items-center gap-3">
          <label for="description" class="w-25">Description: </label>
          <input type="text" name="description" id="description" minlength="3" maxlength="2000" placeholder="Type the description..." v-model="newPizza.description" required>
        </div>
      </div>
      
      <div class="my-4">
        <div class="d-flex align-items-center gap-3">
          <label for="url" class="w-25">Image URL: </label>
          <input type="text" name="url" id="url" minlength="3" maxlength="2000" placeholder="Type the image URL..." v-model="newPizza.url" required>
          <img th:if="*{id != 0}" th:src="*{url}" width="200" />
        </div>
      </div>
      
      <div class="my-4">
        <div class="d-flex align-items-center gap-3">
          <label for="price" class="w-25">Price (in cents of €): </label>
          <input type="number" name="price" id="price" min="1" placeholder="Type the price (in cents of €)..." v-model="newPizza.price" required>
        </div>
      </div>
      
      <br />
      
      <button class="btn btn-outline-light" type="submit">CREATE</button>
      
    </form>
  </main>
</template>
