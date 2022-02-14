import React from "react";

class SelectForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            value: 'coconut'
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState({value: event.target.value});
    }

    handleSubmit(event) {
        alert('Ваш любимый вкус: ' + this.state.value);
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Выберите ваш любимыый вкус:
                    <select value={this.state.value} onChange={this.handleChange}>
                        <option value={'grapefruit'}>Грейпфрут</option>
                        <option value={'lime'}>Лайм</option>
                        <option value={'coconut'}>Кокос</option>
                        <option value={'mango'}>Манго</option>
                    </select>
                </label>
                <input type={'submit'} value={'Отправить'}/>
            </form>
        )
    }
}

export { SelectForm };