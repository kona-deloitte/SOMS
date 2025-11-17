const API_BASE = "";

async function loadProducts() {
  const res = await fetch(`${API_BASE}/products`);
  const data = await res.json();
  renderTable(data, ["id", "sku", "name", "price", "quantity", "category"]);
}

async function loadOrders() {
  const res = await fetch(`${API_BASE}/orders`);
  const data = await res.json();
  renderTable(data, ["id", "userId", "productId", "quantity", "totalAmount", "paymentStatus"]);
}

async function loadPayments() {
  const res = await fetch(`${API_BASE}/payments`);
  const data = await res.json();
  renderTable(data, ["id", "orderId", "amount", "paymentMode", "status"]);
}

async function loadUsers() {
  const res = await fetch(`${API_BASE}/users`);
  const data = await res.json();
  renderTable(data, ["id", "name", "email", "phone", "address"]);
}

function renderTable(data, columns) {
  const content = document.getElementById("content");
  if (!Array.isArray(data) || data.length === 0) {
    content.innerHTML = "<p>No data available.</p>";
    return;
  }

  let table = "<table><thead><tr>";
  columns.forEach(col => (table += `<th>${col}</th>`));
  table += "</tr></thead><tbody>";

  data.forEach(item => {
    table += "<tr>";
    columns.forEach(col => (table += `<td>${item[col] ?? ""}</td>`));
    table += "</tr>";
  });

  table += "</tbody></table>";
  content.innerHTML = table;
}
