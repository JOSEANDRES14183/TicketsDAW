import React from "react";

class ErrorBoundaryHide extends React.Component {
    //TODO: It works, but it logs errors to the console in a loop when it catches something

    constructor(props) {
        super(props);
        this.state = { hasError: false };
    }

    state = { errorCount: 0 }

    componentDidCatch() {
        this.setState((state) => ({ errorCount: state.errorCount + 1 }))
    }

    static getDerivedStateFromError(error) {
        // Update state so the next render will show the fallback UI.
        return { hasError: true };
    }

    componentDidCatch(error, errorInfo) {
        // You can also log the error to an error reporting service
        console.warn(error, errorInfo);
    }

    render() {
        return(
            <React.Fragment key={this.state.errorCount}>
                {this.props.children}
            </React.Fragment>
        )
        if (this.state.hasError) {
            // You can render any custom fallback UI
            return <h2>Something went wrong.</h2>;
        }

        return this.props.children;
    }
}

export default ErrorBoundaryHide;