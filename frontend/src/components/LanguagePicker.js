import {DropdownItem, DropdownMenu, DropdownToggle, UncontrolledDropdown} from "reactstrap";
import {useTranslation} from "react-i18next";

function LanguagePicker(props){

    const { t, i18n } = useTranslation();

    return (
        <UncontrolledDropdown className={"my-3 my-md-0 me-3"}>
            <DropdownToggle caret color={props.theme}>
                <span className={"text-uppercase"}>
                    {t('lang')}
                </span>
            </DropdownToggle>
            <DropdownMenu>
                <DropdownItem onClick={() => i18n.changeLanguage("es")}>Español</DropdownItem>
                <DropdownItem onClick={() => i18n.changeLanguage("ca")}>Català</DropdownItem>
                <DropdownItem onClick={() => i18n.changeLanguage("en")}>English</DropdownItem>
            </DropdownMenu>
        </UncontrolledDropdown>
    );
}

export default LanguagePicker;