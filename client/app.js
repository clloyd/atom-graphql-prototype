import React from 'react';
import {render} from 'react-dom';

class Prototype extends React.Component {
  render () {
    return (
      <div>Hello World</div>
    )
  }
}

render(<Prototype />, document.getElementById('react-output'));
