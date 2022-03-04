import {Button} from "reactstrap";
import React from "react";
import {ThemeContext} from "../contexts/ThemeContext";

function ThemeToggler(props){
    return (
        <ThemeContext.Consumer>
            {(value) => (
                <Button className={"my-3 my-md-0 me-3 text-" + (value==='light' ? 'dark' : 'light')}
                        onClick={props.toggleTheme}
                        color={value}
                        outline={true}>
                    <i className={"bi bi-brightness-high" + (value==='light' ? '-fill' : '')} />
                </Button>
            )}
        </ThemeContext.Consumer>
    );
}

export default ThemeToggler;