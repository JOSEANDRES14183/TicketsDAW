import {useState} from "react";
import {Dropdown, DropdownItem, DropdownMenu, DropdownToggle, UncontrolledDropdown} from "reactstrap";
import Translate from "../locales/Translate";

function LanguagePicker(props){

    return (
        <UncontrolledDropdown className={"my-3 my-md-0 me-3"}>
            <DropdownToggle caret>
                <span className={"text-uppercase"}>
                    <Translate string={"lang"} />
                </span>
            </DropdownToggle>
            <DropdownMenu>
                <DropdownItem onClick={() => props.changeLang("es")}>Español</DropdownItem>
                <DropdownItem onClick={() => props.changeLang("ca")}>Català</DropdownItem>
                <DropdownItem onClick={() => props.changeLang("en")}>English</DropdownItem>
            </DropdownMenu>
        </UncontrolledDropdown>
    );
}

export default LanguagePicker;