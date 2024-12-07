//FileName: server.js
//studentname: Nandini
//studentId: 200596236
//Date: November 14

const app = require('./app'); // Import the app from app.js

const PORT = process.env.PORT || 5000;

app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});
