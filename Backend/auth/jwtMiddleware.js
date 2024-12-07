//FileName: jwt.js
//studentname: Nandini
//studentId: 200596236
//Date: Dec 04

// jwtMiddleware.js
const jwt = require('jsonwebtoken');
require('dotenv').config();

const authenticateToken = (req, res, next) => {
    const authHeader = req.header('Authorization');
    const token = authHeader && authHeader.split(' ')[1];

    if (!token) return res.status(401).json({ message: 'Access denied, no token provided' });

    jwt.verify(token, process.env.JWT_SECRET, (err, user) => {
        if (err) return res.status(403).json({ message: 'Invalid token' });
        req.user = user;
        next();
    });
};
getUserId = (req, res) => {
    let token = req.headers["x-access-token"];
    let id = "";
    if (!token) {
      return res.status(403).send({
        message: "No token provided!"
      });
    }
  
    jwt.verify(token,
        config.secret,
        (err, decoded) => {
          if (err) {
            return res.status(401).send({
              message: "Unauthorized!",
            });
          }
          id = decoded.id;
          return id;
        });
  };

module.exports = authenticateToken;
