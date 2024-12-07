//FileName: app.js
//studentname: Nandini
//studentId: 200596236
//Date: November 14
// app.js

// Importing required packages
const express = require('express');
const dotenv = require('dotenv');
const connectDB = require('./config/db');
const recipeRoutes = require('./routes/recipeRoutes');
const userRoutes = require('./routes/userRoutes');

// Load environment variables from .env file
dotenv.config();

// Create an instance of Express
const app = express();

// Connect to the MongoDB database
connectDB();

// Middleware to parse JSON requests
app.use(express.json());

// Define routes for the API
app.use('/api/list', recipeRoutes);
app.use('/api/users', userRoutes);

// Export the app for use in server.js
module.exports = app;
