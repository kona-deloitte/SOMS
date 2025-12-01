const API = "http://localhost:8080";

// Load categories
async function loadCategories() {
    const res = await fetch(`${API}/products`);
    const products = await res.json();

    let categories = [...new Set(products.map(p => p.category).filter(Boolean))];

    let select = document.getElementById("categoryFilter");
    if (!select) return;

    select.innerHTML = `<option value="">All</option>` +
        categories.map(c => `<option value="${c}">${c}</option>`).join("");
}

// Fetch products
async function fetchProducts() {
    const category = document.getElementById("categoryFilter")?.value;
    const res = await fetch(`${API}/products`);
    const data = await res.json();

    const filtered = category ? data.filter(p => p.category === category) : data;

    document.getElementById("productList").innerHTML =
        filtered.map(p => `
           <div class="card">
                <h3>${p.name}</h3>
                <p>Category: ${p.category}</p>
                <p>Price: Rs. ${p.price}</p>
                <p>Qty: ${p.quantity}</p>

                <button onclick="goToBuy(${p.id})">
                    <span class="material-icons">shopping_cart</span> Buy
                </button>
           </div>
        `).join("");
}

// Redirect to buy page
function goToBuy(id) {
    localStorage.setItem("productToBuy", id);
    location.href = "placeOrder.html";
}

// Place Order
async function placeOrderFromUI() {
    const req = {
        userId: Number(document.getElementById("orderUser").value),
        productId: Number(document.getElementById("orderProduct").value),
        quantity: Number(document.getElementById("orderQty").value),
        paymentMode: document.getElementById("orderMode").value
    };

    let res = await fetch(`${API}/orders`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(req)
    });

    let json = await res.json();
    document.getElementById("orderResult").innerHTML =
        `<div class="card"> ${JSON.stringify(json)} </div>`;
}

// User profile
async function fetchUserDetails() {
    const id = document.getElementById("userId").value;
    const res = await fetch(`${API}/users/${id}`);

    if (!res.ok) return alert("User not found!");

    const u = await res.json();

    document.getElementById("userDetails").innerHTML = `
        <div class="card">
            <h3>${u.name}</h3>
            <p>Email: ${u.email}</p>
            <p>Phone: ${u.phone}</p>
            <p>Address: ${u.address}</p>
        </div>
    `;
    document.getElementById("userActions").style.display = "block";
}

// Fetch orders
async function fetchOrdersByUser() {
    const id = document.getElementById("orderUserId").value;
    const res = await fetch(`${API}/orders/user/${id}`);
    const data = await res.json();

    document.getElementById("ordersList").innerHTML =
        data.map(o => `
            <div class="card">
                <h3>Order #${o.id}</h3>
                <p>Amount: Rs. ${o.totalAmount}</p>
                <p>Status: ${o.paymentStatus}</p>
                <p>Discount: ${o.discountApplied ?? "N/A"}</p>
            </div>
        `).join("");
}

// Fetch payments
async function fetchPaymentsByUser() {
    const id = document.getElementById("paymentUserId").value;
    const res = await fetch(`${API}/payments/byUser/${id}`);
    let data = await res.json();
    if (!Array.isArray(data)) data = [data];
//    const data = await res.json();

    document.getElementById("paymentsList").innerHTML =
        data.map(p => `
            <div class="card">
                <h3>Payment #${p.id}</h3>
                <p>Amount: Rs. ${p.amount}</p>
                <p>Status: ${p.status}</p>
                <p>Mode: ${p.paymentMode}</p>
            </div>
        `).join("");
}
