import { Notify } from "quasar";

const error = (message) => {
  Notify.create({
    message,
    progress: true,
    type: "negative",
    color: "negative",
  });
};

const errorPermanent = (message) => {
  Notify.create({
    message,
    type: "negative",
    color: "negative",
    timeout: 0,
    actions: [
      { icon: 'close', color: 'white', round: true, handler: () => { /* ... */ } }
    ]
  });
};

const succes = (message) => {
  Notify.create({
    message,
    progress: true,
    type: "positive",
    color: "positive",
  });
};

const warning = (message) => {
  Notify.create({
    message,
    progress: true,
    type: "warning",
    color: "warning",
  });
};

const info = (message) => {
  Notify.create({
    message,
    progress: true,
    type: "info",
    color: "info",
  });
};

export default {
  error,
  errorPermanent,
  succes,
  warning,
  info,
};
