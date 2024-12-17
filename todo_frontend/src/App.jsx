import { BrowserRouter } from "react-router-dom";
import "./App.css";
import Todo from "./page/Todo";
import AuthProvider from "./provider/AuthProvider";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <AuthProvider>
          <Todo />
        </AuthProvider>
      </BrowserRouter>
    </div>
  );
}

export default App;
