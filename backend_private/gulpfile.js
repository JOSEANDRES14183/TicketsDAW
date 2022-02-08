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

function copyJQuery(){
    return gulp.src('node_modules/jquery/dist/jquery.min.js*')
        .pipe(gulp.dest('src/main/resources/static/js'));
}

function copyDataTablesCss(){
    return gulp.src('node_modules/datatables.net-bs5/css/dataTables.bootstrap5.min.css')
        .pipe(gulp.dest('src/main/resources/static/css'));
}

function copyDataTablesJs(){
    return gulp.src(['node_modules/datatables.net/js/jquery.dataTables.min.js*', 'node_modules/datatables.net-bs5/js/dataTables.bootstrap5.min.js*'])
        .pipe(gulp.dest('src/main/resources/static/js'));
}

function copyDataTablesResponsiveCss(){
    return gulp.src('node_modules/datatables.net-responsive-bs5/css/responsive.bootstrap5.min.css')
        .pipe(gulp.dest('src/main/resources/static/css'));
}

function copyDataTablesResponsiveJs(){
    return gulp.src(['node_modules/datatables.net-responsive/js/dataTables.responsive.min.js*', 'node_modules/datatables.net-responsive-bs5/js/responsive.bootstrap5.js*'])
        .pipe(gulp.dest('src/main/resources/static/js'));
}

exports.compilaSass = buildStyles;
exports.copy = copyBootstrapFiles;
exports.build = parallel(buildStyles, copyBootstrapFiles, copyBootstrapIconsCss, copyBootstrapIconsFonts, copyFullCalendarJs, copyFullCalendarCss, copyJQuery, copyDataTablesCss, copyDataTablesJs, copyDataTablesResponsiveCss, copyDataTablesResponsiveJs);