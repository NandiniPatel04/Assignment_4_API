//FileName: userroutes.js
//studentname: Nandini
//studentId: 200596236
//Date: Dec 05

const express = require('express');
const verifySignUp = require('../auth/verifySignUp')
const { registerUser, loginUser, logoutUser } = require('../controllers/userController');

const router = express.Router();

router.post('/register',[verifySignUp], registerUser); // POST /users/register
router.post('/login', loginUser); // POST /users/login
router.post('/logout', logoutUser); // POST /users/logout

module.exports = router;
