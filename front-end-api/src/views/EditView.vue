<script setup>
  import axios from 'axios';

  import { onMounted, ref } from 'vue';
  import { useRoute, useRouter } from 'vue-router';

  const route = useRoute();
  const router = useRouter();

  const API_URL = "http://localhost:8080/api/v1/pizzas";
  
  const newPizza = ref({
    name: "",
    description: "",
    url: "",
    price: 0
  })

  const pizzaId = route.params.id;

  const pizza = ref({
    id: 0,
    name: "",
    description: "",
    url: "",
    price: 0
  });

  onMounted(() => {
    fetchPizza(pizzaId);
  })

  function fetchPizza(id) {
    axios
    .post(`${API_URL}/${id}`)
    .then(res => {
      if (res.status === 200) {
        pizza.value = res.data;
      }
    })
    .catch(error => {
      console.error(error);
    })
  }

  function updatePizza() {
    axios
    .put(`${API_URL}/${pizzaId}`, pizza.value)
    .then(res => {
      if (res.status === 200) {
        router.push("/");
      }
    })
    .catch(error => {
      console.error(error);
      fetchPizza(pizzaId)
    })
  }
</script>

<template>
  <main class="container py-5">
    <h1 class="text-center">EDIT PIZZA</h1>

    <form v-if="pizza.id != 0" method="post" @submit.prevent="updatePizza()">
      
      <div class="my-4">
        <div class="d-flex align-items-center gap-3">
          <label for="name" class="w-25">Name: </label>
          <input type="text" name="name" id="name" minlength="3" maxlength="100" placeholder="Type the name..." v-model="pizza.name" required>
        </div>
      </div>
      
      <div class="my-4">
        <div class="d-flex align-items-center gap-3">
          <label for="description" class="w-25">Description: </label>
          <input type="text" name="description" id="description" minlength="3" maxlength="2000" placeholder="Type the description..." v-model="pizza.description" required>
        </div>
      </div>
      
      <div class="my-4">
        <div class="d-flex align-items-center gap-3">
          <label for="url" class="w-25">Image URL: </label>
          <input type="text" name="url" id="url" minlength="3" maxlength="2000" placeholder="Type the image URL..." v-model="pizza.url" required>
          <img th:if="*{id != 0}" th:src="*{url}" width="200" />
        </div>
      </div>
      
      <div class="my-4">
        <div class="d-flex align-items-center gap-3">
          <label for="price" class="w-25">Price (in cents of €): </label>
          <input type="number" name="price" id="price" min="1" placeholder="Type the price (in cents of €)..." v-model="pizza.price" required>
        </div>
      </div>
      
      <br />
      
      <button class="btn btn-outline-light" type="submit">UPDATE</button>
      
    </form>
  </main>
</template>
