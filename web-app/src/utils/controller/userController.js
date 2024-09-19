import { api } from "src/boot/axios";

let searchUsers = (search) => {
  const param = new FormData();
  param.append("search", search);
  return api.post("user/search-user", param);
};

let createUser = (user) => {
  return api.post("/user", user);
};

let getUserById = (userId) => {
  const data = api.get(`/user/${userId}`);
  return data;
};

let updateUser = (userId, user) => {
  return api.put(`/user/${userId}`, user);
};

let deleteUser = (userId) => {
  return api.delete(`/user/${userId}`);
};

export const userController = {
  searchUsers,
  createUser,
  getUserById,
  updateUser,
  deleteUser
};
