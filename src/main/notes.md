# Steps
1. Main and Secondary Resources
2. URLs and Methods
3. Requests and Responses

# 1 Resources
/customers
    _view, search, and query products in different ways_
    _add products to shopping cart_
    _make an order of the current shopping cart_
    _cancel an order_
    _view order status_
    _view previously made orders_ 

/clerks
    _CRUD on products_
    _approve customer orders_
    _update order status_

/admins
    _manage the store_
    _manage CRUD clerk accounts_
    _can do anything a clerk can do on the API_

/products

/categories 

# 2 URLs and Methods

## categories
/categories
xGET: returns all categories
xPOST: adds a new category

/categories/{id}
xGET: returns that particular category
xPUT: updates the category if it exists, otherwise adds it
xDELETE: deletes the categories

/categories/{id}/products
xGET: view all products for that given id

## products
/products
    xGET: returns paginated products 
    xPOST: adds a new product

/products/{id}
    xGET: returns that particular product
    xPUT: updates that particular product if it exists, otherwise adds it 
    xDELETE: deletes the product

/products/{id}/categories
    <!-- xGET: gets all categories for that product -->
    xPOST: adds a new category for that product

/products/{id}/categories/{id}
    xDELETE: deletes that category from that product

**/products search**
/products?q="search+string"
    GET: searches for that string in the product name, description, and categories

## customers
/customers
    GET
    POST

/customers/{id}
    GET
    PUT
    DELETE

/customers/{id}/cart
    POST: add an item to that customer's shopping cart (create the cart if it doesn't exist)
    DELETE: empty that customer's shopping cart
    GET: get the contents of that customer's shopping cart

/customers/{id}/orders
    GET: get all orders for that customer
    POST: create a new order from that customer's shopping cart if it's not empty, then empty it

/customers/{id}/orders/{id}
    GET: get that particular order
    PATCH: update order status

## orders
/orders
    GET: get all orders paginated
    
/orders/{id}
    GET: get that particular order