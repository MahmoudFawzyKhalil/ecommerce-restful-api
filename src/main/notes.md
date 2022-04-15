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
/products
    xGET: returns paginated products [x]
    xPOST: adds a new product [x]

/products/{id}
    xGET: returns that particular product [x]
    xPUT: updates that particular product if it exists, otherwise adds it 
    xDELETE: deletes the product [x]

/products/{id}/categories
    <!-- xGET: gets all categories for that product -->
    xPOST: adds a new category for that product [x]

/products/{id}/categories/{id}
    xDELETE: deletes that category from that product [x]

**/products search**
/products?q="search+string"
    GET: searches for that string in the product name, description, and categories [x]

--------------------------------------------------------

/categories
    xGET: returns all categories [x]
    xPOST: adds a new category [x]

/categories/{id}
    xGET: returns that particular category [x]
    xPUT: updates the category if it exists, otherwise adds it [x]
    xDELETE: deletes the category [x]

    xGET: view all products for that given id [x]

--------------------------------------------------------

/clerks
    xGET
    xPOST

/clerks/{id}
    xGET
    xPUT
    xDELETE

--------------------------------------------------------

/customers
    xGET
    xPOST

/customers/{id}
    xGET
    xPUT
    xDELETE

/customers/{id}/orders
    xGET
    xPOST

/customers/{id}/orders/{id}
    xGET
    xPUT: create or update an order, change order status, approve order, cancel order

--------------------------------------------------------

/orders
    xGET: get all orders paginated
