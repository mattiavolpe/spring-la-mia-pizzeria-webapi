<script setup>
  import axios from 'axios';

  import { onMounted, ref } from 'vue';

  const API_URL = "http://localhost:8080/api/v1/pizzas";

  const pizzas = ref([]);

  onMounted(() => {
    fetchAllPizzas();
  })

  function fetchAllPizzas() {
    axios
    .get(API_URL)
    .then(res => {
      pizzas.value = res.data;
    })
    .catch(error => {
      console.error(error);
    })
  }

  function deletePizza(id) {
    axios
    .delete(`${API_URL}/${id}`)
    .then(res => {
      if (res.data === "Successfully removed") {
        fetchAllPizzas();
      }
    })
    .catch(error => {
      console.error(error);
    })
  }
  
</script>

<template>
  <main class="container py-5">
    <h1 class="text-center">PIZZAS</h1>
    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-5">
      <div v-for="pizza in pizzas" class="col">
        <div class="card h-100">
          <div class="card-header">
            <img :src="pizza.url" alt="" class="card-img-top">
          </div>
          <div class="card-body text-center">
            <h3 class="mb-0">{{ pizza.name }}</h3>
          </div>
          <div class="card-footer text-center">
            <button class="text-danger fw-bold border-0 bg-transparent delete_button" @click="deletePizza(pizza.id)">DELETE PIZZA</button>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<style lang="scss" scoped>
  img {
    aspect-ratio: 16 / 9;
    object-fit: contain;
  }

  .delete_button {
    cursor: pointer;
  }
</style>
