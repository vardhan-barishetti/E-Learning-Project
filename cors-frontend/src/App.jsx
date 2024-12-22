import { useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import "./App.css";

function App() {
  const apiUrl = `http://localhost:8080/api/v1/categories`;
  function getData() {
    console.log("getting data");
    //logic to get data fron server(backend):
    fetch(apiUrl)
      .then((response) => {
        response
          .json()
          .then((data) => {
            console.log(data);
          })
          .catch((error) => {
            console.log(error);
          });
      })
      .catch((error) => {
        console.log(error);
      });
  }

  return (
    <>
      <h1>Cors Example</h1>
      <h1>Popular Categories</h1>
      <div className="card">
        <button onClick={getData}>Get Data</button>
      </div>
      <p className="read-the-docs">

      </p>
      <div>
        <img src={reactLogo} alt="React Logo" />
        <img src={viteLogo} alt="Vite Logo" />
        <p className="read-the-docs">
          Developed by vardhan

        </p>




      </div>
    </>
  );
}

export default App;