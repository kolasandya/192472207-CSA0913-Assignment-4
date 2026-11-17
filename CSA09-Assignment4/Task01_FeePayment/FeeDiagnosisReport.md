# Task 1.1 - Design Diagnosis
1. **Separation of Interface and Logic:** The original code combines GUI components, SQL queries, and database credentials directly inside the event listener.
2. **String Comparison:** Uses `==` instead of `.equals("Pay")`, which can fail due to reference equality checks.
3. **Input Validation:** Directly parses text without checking if fields are empty or numeric, throwing unhandled `NumberFormatException`.
4. **EDT Blocking & Thread.sleep():** Executes blocking JDBC calls and `Thread.sleep(3000)` directly on the Event Dispatch Thread (EDT), freezing the entire GUI window.
5. **Security Vulnerability:** Constructs SQL statements via raw string concatenation (`+ regNo.getText() +`), exposing the application to SQL Injection.
