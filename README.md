## **Internet-shop**	

The following technologies were used: 

      Spring Boot, Spring Security, Hibernate, Flyway, Thymeleaf

#### The app is available at the link: https://my-pet-app.herokuapp.com/

Default users:

    ADMIN
    login: admin 
    password: admin

    USER
    login: user 
    password: user

The store has a catalog of goods for which you need to realize the opportunity to:
- sort by product name (az, za);
- sort of goods by price (from cheap to expensive, from expensive to cheap);
- sorting goods by novelty;
- sample of goods by parameters (category, price range, color, size, etc.)


- The user browses the catalog and can add items to their cart. After adding the goods to the cart, the registered user can place an order. This option is not available for an unregistered user. After placing an order, it (order) recieve the status 'registered'.
- The user has a personal account where he can view his orders.
- The user, that was blocked, can't log in to his account.


- The system administrator has the rights:
  - adding / removing goods, changing product information;
  - blocking / unblocking the user;
  - transfering order from 'registered' to 'paid' or 'canceled'.


- Known Issues:
  - If the admin deletes a product, that exists in the user's order, this product disappears from that order.
  - If product images don't show, it means that I haven't changed the Dropbox token yet :)