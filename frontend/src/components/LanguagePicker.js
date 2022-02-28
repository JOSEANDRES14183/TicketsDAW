import {useState} from "react";
import {Dropdown, DropdownItem, DropdownMenu, DropdownToggle, UncontrolledDropdown} from "reactstrap";

function LanguagePicker(props){

    return (
        <UncontrolledDropdown>
            <DropdownToggle caret>
                ES
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