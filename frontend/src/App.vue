<template>
  <main class="app-shell">
    <section class="shop-panel">
      <div class="shop-copy">
        <span class="eyebrow">35 分钟送达 · 满 49 免配送费</span>
        <h1>小满外卖</h1>
        <p>热饭、汤面、轻食和甜品，一屏完成点单。</p>
      </div>
      <div class="shop-metrics">
        <div>
          <strong>4.8</strong>
          <span>评分</span>
        </div>
        <div>
          <strong>2.3k</strong>
          <span>月售</span>
        </div>
        <div>
          <strong>¥6</strong>
          <span>配送</span>
        </div>
      </div>
    </section>

    <section class="content-grid">
      <div class="menu-area">
        <div class="toolbar">
          <div class="tabs" aria-label="菜品分类">
            <button
              v-for="category in categories"
              :key="category"
              :class="{ active: selectedCategory === category }"
              type="button"
              @click="selectedCategory = category"
            >
              {{ category }}
            </button>
          </div>
          <label class="search-box">
            <Search :size="18" />
            <input v-model.trim="keyword" placeholder="搜索菜品" />
          </label>
        </div>

        <p v-if="loading" class="state-text">菜单加载中...</p>
        <p v-else-if="error" class="state-text">{{ error }}</p>

        <div v-else class="menu-list">
          <article v-for="item in filteredMenu" :key="item.id" class="dish-card">
            <img :src="item.imageUrl" :alt="item.name" />
            <div class="dish-info">
              <div class="dish-title">
                <h2>{{ item.name }}</h2>
                <span v-if="item.signature">招牌</span>
              </div>
              <p>{{ item.description }}</p>
              <div class="dish-footer">
                <div>
                  <strong>¥{{ formatMoney(item.price) }}</strong>
                  <small>月售 {{ item.monthlySales }}</small>
                </div>
                <button class="icon-button" type="button" :aria-label="`添加 ${item.name}`" @click="addToCart(item)">
                  <Plus :size="20" />
                </button>
              </div>
            </div>
          </article>
        </div>
      </div>

      <aside class="cart-panel">
        <div class="cart-header">
          <h2>购物车</h2>
          <button class="ghost-button" type="button" :disabled="cart.length === 0" @click="cart = []">
            <Trash2 :size="17" />
            清空
          </button>
        </div>

        <div v-if="cart.length === 0" class="empty-cart">
          <ShoppingBag :size="34" />
          <p>还没有选择菜品</p>
        </div>

        <div v-else class="cart-lines">
          <div v-for="line in cart" :key="line.id" class="cart-line">
            <div>
              <strong>{{ line.name }}</strong>
              <span>¥{{ formatMoney(line.price) }}</span>
            </div>
            <div class="quantity-control">
              <button type="button" :aria-label="`减少 ${line.name}`" @click="decrease(line.id)">
                <Minus :size="15" />
              </button>
              <span>{{ line.quantity }}</span>
              <button type="button" :aria-label="`增加 ${line.name}`" @click="increase(line.id)">
                <Plus :size="15" />
              </button>
            </div>
          </div>
        </div>

        <form class="checkout-form" @submit.prevent="submitOrder">
          <label>
            联系人
            <input v-model.trim="form.customerName" required placeholder="李女士" />
          </label>
          <label>
            手机号
            <input v-model.trim="form.phone" required placeholder="13800000000" />
          </label>
          <label>
            配送地址
            <input v-model.trim="form.address" required placeholder="上海市静安区..." />
          </label>
          <label>
            备注
            <textarea v-model.trim="form.note" maxlength="120" placeholder="少辣、不要香菜等"></textarea>
          </label>

          <div class="total-row">
            <span>合计</span>
            <strong>¥{{ formatMoney(totalAmount) }}</strong>
          </div>

          <button class="submit-button" type="submit" :disabled="submitting || cart.length === 0">
            <LoaderCircle v-if="submitting" class="spin" :size="18" />
            <CreditCard v-else :size="18" />
            提交订单
          </button>
        </form>

        <div v-if="orderResult" class="order-result">
          <CheckCircle2 :size="18" />
          <div>
            <strong>{{ orderResult.status }}：{{ orderResult.orderNo }}</strong>
            <span>预计 {{ orderResult.estimatedMinutes }} 分钟送达</span>
          </div>
        </div>
      </aside>
    </section>
  </main>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import {
  CheckCircle2,
  CreditCard,
  LoaderCircle,
  Minus,
  Plus,
  Search,
  ShoppingBag,
  Trash2
} from 'lucide-vue-next'
import { mockMenu } from './mockMenu'

const menu = ref([])
const cart = ref([])
const loading = ref(true)
const submitting = ref(false)
const error = ref('')
const selectedCategory = ref('全部')
const keyword = ref('')
const orderResult = ref(null)

const form = reactive({
  customerName: '',
  phone: '',
  address: '',
  note: ''
})

const categories = computed(() => ['全部', ...new Set(menu.value.map((item) => item.category))])

const filteredMenu = computed(() => {
  return menu.value.filter((item) => {
    const matchesCategory = selectedCategory.value === '全部' || item.category === selectedCategory.value
    const matchesKeyword = !keyword.value || item.name.includes(keyword.value) || item.description.includes(keyword.value)
    return matchesCategory && matchesKeyword
  })
})

const totalAmount = computed(() => cart.value.reduce((sum, line) => sum + Number(line.price) * line.quantity, 0))

function formatMoney(value) {
  return Number(value).toFixed(2)
}

function addToCart(item) {
  const existing = cart.value.find((line) => line.id === item.id)
  if (existing) {
    existing.quantity += 1
    return
  }
  cart.value.push({ ...item, quantity: 1 })
}

function increase(id) {
  const line = cart.value.find((item) => item.id === id)
  if (line) line.quantity += 1
}

function decrease(id) {
  const line = cart.value.find((item) => item.id === id)
  if (!line) return
  if (line.quantity === 1) {
    cart.value = cart.value.filter((item) => item.id !== id)
    return
  }
  line.quantity -= 1
}

async function fetchMenu() {
  loading.value = true
  error.value = ''
  try {
    const response = await fetch('/api/menu')
    if (!response.ok) throw new Error('菜单获取失败')
    menu.value = await response.json()
  } catch (err) {
    menu.value = mockMenu
    error.value = ''
  } finally {
    loading.value = false
  }
}

async function submitOrder() {
  submitting.value = true
  orderResult.value = null
  try {
    const response = await fetch('/api/orders', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        ...form,
        items: cart.value.map((line) => ({ menuItemId: line.id, quantity: line.quantity }))
      })
    })
    if (!response.ok) throw new Error('下单失败，请稍后重试')
    orderResult.value = await response.json()
    cart.value = []
    form.note = ''
  } catch (err) {
    orderResult.value = {
      orderNo: `DEMO${Date.now().toString().slice(-6)}`,
      status: '演示订单已生成',
      estimatedMinutes: 35
    }
    cart.value = []
    form.note = ''
  } finally {
    submitting.value = false
  }
}

onMounted(fetchMenu)
</script>
