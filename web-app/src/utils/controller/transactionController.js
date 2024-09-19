import { api } from "src/boot/axios";

let getAllTransactions = () => {
  return api.get("/transaction");
};

let searchTransactionPage = (search) => {
  const param = new FormData();
  param.append("search", search);
  return api.post("/transaction/transaction-page", param);
};

let createTransaction = (transaction) => {
  return api.post("/transaction", transaction);
};

let getTransactionById = (transactionId) => {
  return api.get(`/transaction/${transactionId}`);
};

let deleteTransaction = (transactionId) => {
  return api.delete(`/transaction/${transactionId}`);
};

let getAllTransactionsByUserId = (userId) => {
  return api.get(`/transaction/user/${userId}`);
};

export const transactionController = {
  getAllTransactions,
  createTransaction,
  getTransactionById,
  deleteTransaction,
  getAllTransactionsByUserId,
  searchTransactionPage
};
