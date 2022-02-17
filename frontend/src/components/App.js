import Header from './Header.js';
import Footer from './Footer.js';
import axios from "axios";
import {useEffect, useState} from "react";
import EventoCard from "./EventoCard";

function App() {

    const [isLoading, setLoading] = useState(false);
    const [data, setData] = useState([]);
    const [error, setError] = useState(null);

    useEffect(()=>{
        setLoading(true);
        axios.get("http://" + process.env.REACT_APP_API_HOST + "/api/eventos")
            .then(result =>
                setData(result.data),
                setLoading(false),
            )
            .catch(error => 
                setError(error),
                setLoading(false)
            );
    },[setData]);

    if (isLoading){
        return <p>Loading...</p>
    }

    if(error){
        return <p>{error}</p>
    }

  return (
    <>
        <Header />
        <main>
            <section className={"mt-4 container-md d-flex justify-content-center gap-3"}>
                {data.map(function (item,key){
                    return (
                        <EventoCard key={key} evento={item}/>
                    )
                })}
            </section>
        </main>
        <Footer />
    </>
  );

}

export default App;
