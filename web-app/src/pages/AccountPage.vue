<template>
  <q-page class="q-pa-lg">
    <div class="center">
      <div>
        <div class="titre q-my-md">
          Account balances of {{ user.userFirstName }} {{ user.userName }} :
          {{ user.userAccountBalance }} €
        </div>
        <div class="q-mb-md">
          <apex-line
            :chart-categories="creationDates"
            :chart-data="transactionAmounts"
          />
        </div>
        <q-card-actions class="row justify-evenly q-pb-lg">
          <Btn
            :btnTexte="'Deposite money'"
            :btnIcone="'add'"
            :btn-couleur="'green'"
            @click="openDepositeMoneyPopUp()"
          />
          <Btn
            :btnTexte="'Drop off money'"
            :btnIcone="'remove'"
            :btn-couleur="'red'"
            @click="openDropOffMoneyPopUp()"
          />
        </q-card-actions>
        <q-card-actions class="row justify-evenly q-pb-lg">
          <q-table
            :columns="columns"
            :rows="transactionsList"
            row-key="transactionsId"
            :pagination="pagination"
            :rows-per-page-options="[0]"
          >
            <template #body="props">
              <q-tr
                :props="props"
                :class="{ 'hovered-row': isHovered }"
                @mouseover="isHovered = true"
                @mouseout="isHovered = false"
              >
                <q-td v-for="col in props.cols" :key="col.name" :props="props">
                  <span v-if="col.name == 'delete'">
                    <BtnIcon
                      :btn-icone="'delete'"
                      :btn-couleur="'red'"
                      :btn-size="'xs'"
                      :btn-tooltip="'Deleted'"
                      @click="
                        openDeleteTransactionsPopUp(props.row.transactionsId)
                      "
                    />
                  </span>
                  <span v-else>
                    {{ col.value }}
                  </span>
                </q-td>
              </q-tr>
            </template>
          </q-table>
        </q-card-actions>
      </div>
    </div>
  </q-page>

  <q-dialog v-model="depositeMoneyPopUp">
    <q-card class="text-center q-pa-lg deposite-money-card">
      <q-card-section>
        <span class="sous-titre">Deposite money <q-icon name="savings" /></span>
      </q-card-section>
      <q-card-actions class="row justify-evenly q-pb-lg">
        <TheInput
          v-model="transactionDeposite.transactionsAmount"
          input-label="Money to deposite *"
          :input-type="'number'"
        />
      </q-card-actions>
      <q-card-actions class="row q-pb-lg justify-center">
        <TheInput
          v-model="transactionDeposite.transactionsMessage"
          :input-label="'Message'"
          :input-type="'textarea'"
        />
      </q-card-actions>
      <q-card-actions class="row justify-evenly q-pb-lg">
        <Btn
          :btnTexte="'Cancel'"
          :btnIcone="'cancel'"
          :btn-couleur="'red'"
          @click="cancelDepositeMoneyPopUp()"
        />
        <Btn
          :btnTexte="'Deposite money'"
          :btnIcone="'add'"
          :btn-couleur="'green'"
          @click="depositeMoney()"
        />
      </q-card-actions>
    </q-card>
  </q-dialog>

  <q-dialog v-model="dropOffMoneyPopUp">
    <q-card class="text-center q-pa-lg deposite-money-card">
      <q-card-section>
        <span class="sous-titre">Drop off money <q-icon name="payment" /></span>
      </q-card-section>
      <q-card-actions class="row justify-evenly q-pb-lg">
        <TheInput
          v-model="transactionDropOff.transactionsAmount"
          input-label="Money to drop off *"
          :input-type="'number'"
        />
      </q-card-actions>
      <q-card-actions class="row q-pb-lg justify-center">
        <TheInput
          v-model="transactionDropOff.transactionsMessage"
          :input-label="'Message'"
          :input-type="'textarea'"
        />
      </q-card-actions>
      <q-card-actions class="row justify-evenly q-pb-lg">
        <Btn
          :btnTexte="'Cancel'"
          :btnIcone="'cancel'"
          :btn-couleur="'red'"
          @click="cancelDropOffMoneyPopUp()"
        />
        <Btn
          :btnTexte="'Deposite money'"
          :btnIcone="'remove'"
          :btn-couleur="'green'"
          @click="dropOffMoney()"
        />
      </q-card-actions>
    </q-card>
  </q-dialog>

  <q-dialog v-model="deleteTransactionsPopUp">
    <q-card class="text-center q-pa-lg custom-card">
      <q-card-section>
        <span class="accentue"
          >Are you sure to delete the current transaction : <br />
          {{ transaction.transactionsAmount }} €<q-icon name="savings"
        /></span>
      </q-card-section>
      <q-card-actions class="row justify-evenly q-pb-lg">
        <Btn
          :btnTexte="'Cancel'"
          :btnIcone="'cancel'"
          @click="cancelDeleteTransactionsPopUp()"
        />
        <Btn
          :btnTexte="'Delete'"
          :btnIcone="'delete'"
          :btn-couleur="'red'"
          @click="deleteTransaction()"
        />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
import { defineComponent, defineAsyncComponent } from "vue";
import { UserDto } from "../utils/dto/UserDto.js";
import { TransactionDto } from "src/utils/dto/TransactionDto";
import { transactionController } from "../utils/controller/transactionController.js";
import { userController } from "../utils/controller/userController.js";
import Btn from "src/components/TheButton.vue";
import TheInput from "src/components/TheInput.vue";
import BtnIcon from "src/components/TheButtonIcon.vue";

import notify from "../utils/notify";

const ApexLine = defineAsyncComponent(() =>
  import("src/components/charts/ApexLine.vue")
);

export default defineComponent({
  name: "AccountPage",
  components: {
    ApexLine,
    Btn,
    TheInput,
    BtnIcon,
  },
  data() {
    return {
      user: UserDto(),
      isHovered: false,
      transaction: TransactionDto(),
      transactionsList: [],
      creationDates: [],
      transactionAmounts: [],
      transactionDeposite: TransactionDto(),
      transactionDropOff: TransactionDto(),
      depositeMoneyPopUp: false,
      dropOffMoneyPopUp: false,
      deleteTransactionsPopUp: false,
      pagination: {
        rowsPerPage: 10,
      },
      columns: [
        {
          name: "transactionsAmount",
          required: true,
          label: "Transaction amount",
          field: "transactionsAmount",
          align: "center",
          sortable: true,
        },
        {
          name: "transactionsMessage",
          required: true,
          label: "Transaction message",
          field: "transactionsMessage",
          align: "center",
          sortable: true,
        },
        {
          name: "delete",
          label: "Delete",
          align: "center",
        },
      ],
    };
  },
  created() {
    this.getUserById();
    this.getTransactionsByUserId();
  },

  methods: {
    getUserById() {
      userController
        .getUserById(this.$route.params.userId)
        .then((response) => {
          if (response.data) {
            this.user = response.data;
          }
        })
        .catch((error) => {
          notify.error("Error when picking user's data");
        });
    },
    getTransactionsByUserId() {
      transactionController
        .getAllTransactionsByUserId(this.$route.params.userId)
        .then((response) => {
          if (response.data) {
            this.transactionsList = response.data.transactionDtoList;
            this.creationDates = response.data.transactionCreationList;
            this.transactionAmounts = response.data.transactionAmountList;
          }
        })
        .catch((error) => {
          notify.error("Error when picking transactions list");
        });
    },
    depositeMoney() {
      this.transactionDeposite.transactionsAmount = Math.abs(
        this.transactionDeposite.transactionsAmount
      );
      this.transactionDeposite.transactionsUserId = this.user.userId;
      this.createTransaction(this.transactionDeposite);
      this.depositeMoneyPopUp = !this.depositeMoneyPopUp;
    },
    dropOffMoney() {
      this.transactionDropOff.transactionsAmount = -Math.abs(
        this.transactionDropOff.transactionsAmount
      );
      this.transactionDropOff.transactionsUserId = this.user.userId;
      this.createTransaction(this.transactionDropOff);
      this.dropOffMoneyPopUp = !this.dropOffMoneyPopUp;
    },
    openDepositeMoneyPopUp() {
      this.depositeMoneyPopUp = !this.depositeMoneyPopUp;
    },
    openDropOffMoneyPopUp() {
      this.dropOffMoneyPopUp = !this.dropOffMoneyPopUp;
    },
    cancelDepositeMoneyPopUp() {
      this.depositeMoneyPopUp = !this.depositeMoneyPopUp;
    },
    cancelDropOffMoneyPopUp() {
      this.dropOffMoneyPopUp = !this.dropOffMoneyPopUp;
    },
    getTransactionById(transactionsId) {
      transactionController
        .getTransactionById(transactionsId)
        .then((response) => {
          if (response.data) {
            this.transaction = response.data;
          }
        })
        .catch((error) => {
          notify.error("Error when picking transaction's data");
        });
    },
    createTransaction(transaction) {
      transactionController
        .createTransaction(transaction)
        .then((response) => {
          if (response.data.statusList.length == 0) {
            notify.succes("Transaction created succesfully");
            this.resetTransaction();
            this.getUserById();
            this.getTransactionsByUserId();
          } else {
            response.data.statusList.forEach((status) => {
              notify.error("" + status.message);
            });
          }
        })
        .catch((error) => {
          notify.error("Error during the creation of the transaction");
        });
    },
    resetTransaction() {
      this.transaction = TransactionDto();
      this.transactionDeposite = TransactionDto();
      this.transactionDropOff = TransactionDto();
    },
    deleteTransaction() {
      transactionController
        .deleteTransaction(this.transaction.transactionsId)
        .then((response) => {
          if (response.status == 204) {
            notify.succes("transaction deleted succesfully");
            this.resetTransaction();
            this.getUserById();
            this.getTransactionsByUserId();
            this.cancelDeleteTransactionsPopUp();
          }
        })
        .catch((error) => {
          notify.error("Error during transaction's delete");
        });
    },
    openDeleteTransactionsPopUp(transactionsId) {
      this.deleteTransactionsPopUp = !this.deleteTransactionsPopUp;
      this.getTransactionById(transactionsId);
    },
    cancelDeleteTransactionsPopUp() {
      this.deleteTransactionsPopUp = !this.deleteTransactionsPopUp;
    },
  },
});
</script>
