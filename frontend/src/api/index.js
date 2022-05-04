import axios from "axios";
import { API_BASE_URL } from "../config";

function createInstance() {
  const instance = axios.create({
    baseURL: API_SERVER_URL+"/api",
    headers: {
      "Content-Type": "application/json",
    },
  });
  return instance;
}

function tokenInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL+"/api",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
    },
  });
  return instance;
}
export { createInstance, tokenInstance };
