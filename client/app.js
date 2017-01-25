import React from 'react';
import {render} from 'react-dom';
import ApolloClient, { createNetworkInterface} from 'apollo-client';
import { ApolloProvider, graphql } from 'react-apollo';
import gql from 'graphql-tag';


const client = new ApolloClient({
  networkInterface: createNetworkInterface({ uri: '/graphql' }),
});


const query = gql`
    query query{
      search(query:"trump") {
        id
      }
    }
`;


class Prototype extends React.Component {
  render () {
    return (
      <div>Hello World</div>
    )
  }
}

const PrototypeWithData = graphql(query)(Prototype);



render(
    <ApolloProvider client={client}>
      <PrototypeWithData />
    </ApolloProvider>
    , document.getElementById('react-output'));
