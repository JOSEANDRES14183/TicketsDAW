import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import LanguageDetector from 'i18next-browser-languagedetector';
import es from "./es.json"
import ca from "./ca.json"
import en from "./en.json"

i18n
    .use(LanguageDetector)
    .use(initReactI18next)
    .init({
        debug: true,
        fallbackLng: "es",
        resources: {
            es,
            ca,
            en
        }
    });

export default i18n;