import ImageGallery from "react-image-gallery";

function CarouselEvento(props){

    return (
        <ImageGallery className="w-25" items={props.imagenes}/>
    );

}

export default CarouselEvento;