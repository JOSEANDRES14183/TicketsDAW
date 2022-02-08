const gulp = require('gulp');
const sass = require('gulp-sass')(require('sass'));
const { parallel } = require('gulp');

function buildStyles() {
    return gulp.src('src/main/resources/static/scss/*.scss')
        .pipe(sass().on('error', sass.logError))
        .pipe(gulp.dest('src/main/resources/static/css'));
}

function copyBootstrapFiles(){
    return gulp.src('node_modules/bootstrap/dist/js/bootstrap.bundle.min.js*')
        .pipe(gulp.dest('src/main/resources/static/js'));
}

function copyBootstrapIconsCss(){
    return gulp.src('node_modules/bootstrap-icons/font/bootstrap-icons.css')
        .pipe(gulp.dest('src/main/resources/static/font'));
}

function copyBootstrapIconsFonts(){
    return gulp.src('node_modules/bootstrap-icons/font/fonts/*')
        .pipe(gulp.dest('src/main/resources/static/font/fonts'));
}

function copyFullCalendarJs(){
    return gulp.src('node_modules/fullcalendar/*.js*')
        .pipe(gulp.dest('src/main/resources/static/js/fullcalendar'));
}

function copyFullCalendarCss(){
    return gulp.src('node_modules/fullcalendar/*.css')
        .pipe(gulp.dest('src/main/resources/static/css/fullcalendar'));
}

exports.compilaSass = buildStyles;
exports.copy = copyBootstrapFiles;
exports.build = parallel(buildStyles, copyBootstrapFiles, copyBootstrapIconsCss, copyBootstrapIconsFonts, copyFullCalendarJs, copyFullCalendarCss);