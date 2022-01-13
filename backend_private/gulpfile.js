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

exports.compilaSass = buildStyles;
exports.copy = copyBootstrapFiles;
exports.build = parallel(buildStyles, copyBootstrapFiles);