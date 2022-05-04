import axios from "axios";
import { API_SERVER_URL } from "../config";

function createInstance() {
  const instance = axios.create({
    baseURL: API_SERVER_URL+"/api",
    headers: {
      "Content-Type": "application/json",
    },
  });
  return instance;
}

export { createInstance };

